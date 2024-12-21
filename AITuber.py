import firebase_admin
from firebase_admin import credentials, db
import os
import pprint
from openai import OpenAI
import sounddevice as sd
import soundfile as sf
import time


def initialize_firebase():
    # FirebaseのサービスアカウントキーのJSONファイルへのパスを指定
    firebase_credentials_path = os.getenv("FIREBASE_CREDENTIALS_PATH")  # 環境変数から取得
    database_url = os.getenv("FIREBASE_DATABASE_URL")  # FirebaseのデータベースURL
    cred = credentials.Certificate(firebase_credentials_path)
    firebase_admin.initialize_app(cred, {"databaseURL": database_url})


def get_latest_comment_from_firebase():
    # FirebaseのRealtime Databaseからデータを取得
    ref = db.reference("comments")  # commentsがデータ保存先
    data = ref.get()
    if not data:
        return None
    # 最新のコメントを取得 (例: Firebaseに保存されたデータがタイムスタンプ順であると仮定)
    latest_comment = list(data.values())[-1]["text"]
    return latest_comment


def get_reply(client, comment):
    prompt = "今、簡単な会話をしています。以下のコメントに返信する短文を返してください。"
    res = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{
            "role": "user",
            "content": (
                f"{prompt}"
                f"{comment}"
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
