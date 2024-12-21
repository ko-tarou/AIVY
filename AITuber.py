import firebase_admin
from firebase_admin import credentials, db
import os
from openai import OpenAI
import sounddevice as sd
import soundfile as sf
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
    return latest_comment


def get_reply(client, comment):
    prompt = "今、簡単な会話をしています。以下のコメントに返信する短文を返してください。"
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
    pre_comment = ''
    while True:
        # Firebaseからコメントを取得
        comment = get_latest_comment_from_firebase()
        if comment and pre_comment != comment:
            reply = get_reply(client, comment)
            play_reply(comment, reply)
            pre_comment = comment
        time.sleep(10)


if __name__ == "__main__":
    main()
