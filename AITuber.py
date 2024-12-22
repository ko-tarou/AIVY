import firebase_admin
from firebase_admin import credentials, db
import requests
import os
from openai import OpenAI
import sounddevice as sd
import soundfile as sf
from pynput.keyboard import Controller
import time
import socket
import json


def initialize_firebase():
    # FirebaseのサービスアカウントキーのJSONファイルへのパスを指定
    firebase_credentials_path = "aivy-397ff-firebase-adminsdk-90c4u-7aee19f25e.json"  # 環境変数から取得
    database_url = "https://aivy-397ff-default-rtdb.firebaseio.com/"  # FirebaseのデータベースURL
    cred = credentials.Certificate(firebase_credentials_path)
    firebase_admin.initialize_app(cred, {"databaseURL": database_url})


def get_latest_comment_from_firebase():
    # FirebaseのRealtime Databaseからデータを取得
    ref = db.reference("message")  # messageノードを参照
    latest_comment = ref.get()  # 文字列を直接取得
    print(latest_comment)
    return latest_comment

def get_datails_from_firebase():
    # FirebaseのRealtime Databaseからデータを取得
    ref = db.reference("datails")  # messageノードを参照
    latest_datails = ref.get()  # 文字列を直接取得
    print(latest_datails)
    return latest_datails

def get_summary_from_firebase():
    ref = db.reference("summary")  # messageノードを参照
    latest_summary = ref.get()  # 文字列を直接取得
    #print(latest_history)
    return latest_summary

def add_comments_firebase(comment, reply, i):
    ref = db.reference("/list")
    ref.child(f'{i}:').set({
            "talk":f"{comment}\n{reply}"
            })

def add_summery(summary):
    ref = db.reference("/summary")
    ref.set({
            "summary":f"{summary}"
            })
    
def get_history():
    ref = db.reference("list")  # messageノードを参照
    latest_history = ref.get()  # 文字列を直接取得
    #print(latest_history)
    return latest_history

def get_gender():
    ref = db.reference("selectpeople") #性別の取得
    latest_gender = ref.get()
    #print(latest_gender)
    if latest_gender <= 3:
        gender = "女性"
    else:
        gender = "男性"
    return gender

def change_avater():
    ref = db.reference("selectpeople") #性別の取得
    latest_number = ref.get()

    # UDPメッセージの送信
    port = 56131  # メモしたポート番号に変更
    udp_client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    udp_client.connect(("127.0.0.1", port))

    # JSONデータの構築
    message = {
        "command": "load_setting_file",
        "args": {
            "index": latest_number,
            "load_character": True,
            "load_non_character": True
        }
    }

    # JSONをバイト列に変換して送信
    message_bytes = json.dumps(message).encode('ascii')
    udp_client.send(message_bytes)

    # クライアントを閉じる
    udp_client.close()


def delete_history():
    ref = db.reference("/list")
    for i in range(1,5):
        ref.child(f'{i}:').set({
            "talk":""
        })

def get_summery(client):
    latest_summery = get_history()  # 文字列を直接取得
    #print(latest_summery)
    prompt = f"この会話を要約してください"
    res = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{
            "role": "user",
            "content": (
                f"{prompt} {latest_summery}"
            )
        }]
    )
    summery = res.choices[0].message.content
    return summery

def get_gift():
    ref = db.reference("gift") 
    latest_gift = ref.get()
    ref.update = {"gift":False}
    return latest_gift

def get_emotion(reply, client):
    prompt = f"""
    タスク説明: 以下の質問に対し、与えられた選択肢の中から最も適切な答えを選んでください。

    "joy"例1: 質問: 今日はあまり良いことが無かった
    選択肢:
    1.good
    2.nodding
    3.clap  
    4.""
    答え: ""

    例2: 質問: 友達と遊んで楽しかった。
    選択肢:
    1.good
    2.nodding
    3.clap  
    4.""
    答え: good

    例3: 質問: 美味しいご飯を作れた。 
    選択肢:
    1.good
    2.nodding
    3.clap  
    4.""
    答え: clap

    テスト: 質問: f"{reply}"
    選択肢:
    1.good
    2.nodding
    3.clap  
    4.""
    答え: 
    """
    res = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{
            "role": "user",
            "content": (
                f"{prompt}"
            )
        }]
    )
    emotion = res.choices[0].message.content
    #print(emotion)  # 返信内容の確認
    return emotion

def get_gift_reply(client):
    gender = get_gender()
    details = get_datails_from_firebase()
    prompt = f"今、簡単な会話をしています。あなたは{gender}の{details}、プレゼントをもらったことに対する返信を短文を返してください。"
    res = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{
            "role": "user",
            "content": (
                f"{prompt}"
            )
        }]
    )
    reply = res.choices[0].message.content
    print(reply)  # 返信内容の確認
    return reply

def get_reply(client, comment):
    details = get_datails_from_firebase()
    history = get_history()
    summary = get_summary_from_firebase
    gender = get_gender()
    prompt = f"今、簡単な会話をしています。{summary}や{history}を参考にして、あなたは{gender}の{details}、以下のコメントに返信する短文を返してください。"
    res = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{
            "role": "user",
            "content": (
                f"{prompt} {comment}"
            )
        }]
    )
    reply = res.choices[0].message.content
    print(reply)  # 返信内容の確認
    return reply


def get_reply_if_stop(client, comment):
    details = get_datails_from_firebase()
    history = get_history()
    summary = get_summary_from_firebase
    gender = get_gender()
    prompt = f"今、簡単な会話をしています。会話が止まってしまったので、{summary}や{history}を参考にして、あなたは{gender}の{details}、以下のコメントに返信する短文を返してください。"
    res = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{
            "role": "user",
            "content": (
                f"{prompt} {comment}"
            )
        }]
    )
    reply = res.choices[0].message.content
    print(reply)  # 返信内容の確認
    return reply


def play_reply(reply):
    ref = db.reference("selectpeople")
    latest_number = ref.get()
    if latest_number == 1:
        speaker = 10
    elif latest_number == 2:
        speaker = 20
    elif latest_number == 3:
        speaker = 61
    elif latest_number == 4:
        speaker = 73
    elif latest_number == 5:
        speaker = 53
    elif latest_number == 6:
        speaker = 52
    params = {"text": f"{reply}", "speaker": speaker}
    res = requests.post(
        f'http://127.0.0.1:50021/audio_query',
        params=params
    )
    res = requests.post(
        f'http://127.0.0.1:50021/synthesis',
        params=params,
        json=res.json()
    )
    voice = res.content
    file_path = "aituber-voice.wav"
    with open(file_path, "wb") as f:
        f.write(voice)
    data, fs = sf.read(file_path)
    sd.play(data, fs)
    sd.wait()


def main():
    initialize_firebase()  # Firebase初期化

    client = OpenAI()
    i = 1
    cnt = 0
    summary = ""
    pre_comment = ''
    while True:

        # Firebaseからコメントを取得
        comment = get_latest_comment_from_firebase()
        change_avater()
        """
        gift = get_gift()
        if gift:
            keyboard = Controller()
            keyboard.type("happy")
            gift_reply = get_gift_reply(client)
            play_reply(gift_reply)
        """
        if comment and pre_comment != comment:
            reply = get_reply(client, comment)
            emotion = get_emotion(reply, client)
            print(emotion)
            keyboard = Controller()
            keyboard.type(emotion)
            play_reply(reply)
            pre_comment = comment
            add_comments_firebase(comment, reply, i)
            if i >= 4:
                summary = get_summery(client)
                #print(summary)
                delete_history()
                add_summery(summary)
                i = 1

            else:
                i += 1
        else:
            cnt += 1
        
        if cnt >= 10:
            reply = get_reply_if_stop(client, comment)
            comment = ""
            add_comments_firebase(comment, reply, i)
            play_reply(reply)
            cnt = 0
            time.sleep(10)
        time.sleep(1)
       


if __name__ == "__main__":
    main()
