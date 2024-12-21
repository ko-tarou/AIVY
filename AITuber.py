import firebase_admin
from firebase_admin import credentials, db
import requests
import os
from openai import OpenAI
import sounddevice as sd
import soundfile as sf
from pynput.keyboard import Controller
import time


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

def get_reply(client, comment, summary = ""):
    details = get_datails_from_firebase()
    history = get_history()
    summary = get_summary_from_firebase
    gender = get_gender()
    prompt = f"今、簡単な会話をしています。{summary}や{history}を参考にして、あなたは{gender}で{details}、以下のコメントに返信する短文を返してください。"
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


def play_reply(comment, reply):
    params = {"text": f"{comment} {reply}", "speaker": 3}
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
    summary = ""
    pre_comment = ''
    while True:
        # Firebaseからコメントを取得
        comment = get_latest_comment_from_firebase()
        if comment and pre_comment != comment:
            reply = get_reply(client, comment, summary)
            play_reply(comment, reply)
            pre_comment = comment
            add_comments_firebase(comment, reply, i)
            if i >= 4:
                summary = get_summery(client)
                print(summary)
                delete_history()
                add_summery(summary)
                i = 1

            else:
                i += 1
        time.sleep(3)
       


if __name__ == "__main__":
    main()
