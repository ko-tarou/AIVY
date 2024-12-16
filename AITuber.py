import requests
import os
import pprint
from openai import OpenAI
import sounddevice as sd
import soundfile as sf
import time


def fetch_chat_id(youtube_api_key,live_id):
    params = {
        "key":youtube_api_key,
        "part":"liveStreamingDetails",
        "id":live_id
    }
    res = requests.get("https:www.googleapis.com/youtube/v3/videos",
                params=params)
    chat_id = (res.josn()
              ["items"][0]
              ["liveStreamingDerails"]
              ["activeLiveChatId"])
    return chat_id

def get_latest_comment(youtube_api_key,chat_id): #youtubeから最新コメ取得
    params = {
        "key":youtube_api_key,
        "part":"snippet",
        "liveChatId":chat_id,
        "maxResults":100,
    }
    res = requests.get(
        "https://www.googleapis.com/youtube/v3/liveChat/masseges",
        params=params)
    resource = res.json()
    comments = [x["shippet"]["textMessageDetails"]["messageText"]
                for x in resource["items"]]
    comment = comments[-1]
    return comment

def get_rely(client,comment): #返信用テキスト生成
    prompt = "今、簡単な会話をしています。以下のコメントに返信する短文を返してください。"
    res = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{
            "role":"user",
            "content":(
                f"{prompt}"
                f"{comment}"
            )
        }]
    )
    reply = res.choices[0].message.cotent
    return reply

def play_reply(comment, reply):
    params = {"text": f"{comment} {reply}","speaker": 3}
    res = requests.post(
        f'http://127.0.0.1:50021/audio_query',
        params=params
    )
    res = requests.post(
        f'http://127.0.0.1:50021/audio_query',
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
    youtube_api_key = os.getenv("AIzaSyA6qs0QgYWQ1DoxHBJfqcfKQmPVInoyIiU") #youtubeAPIを入力(google Cloudから取得)
    youtube_url = ("https://www.youtube.com/live/fSWQlPFfyVo")
    base_url = "https://www.youtube.com/live/"
    live_id = "fSWQlPFfyVo"#youtube_url.replace(base_url, "") #liveIDを取得
    chat_id = fetch_chat_id(youtube_api_key,live_id)
    comment = get_latest_comment(youtube_api_key,live_id)
    client = OpenAI()
    reply = get_rely(client,comment)

    pre_comment = ""
    while True:
        comment = get_latest_comment(youtube_api_key, chat_id)
        if pre_comment != comment:
            reply = get_rely(client,comment)
            play_reply(comment, reply)

        time.sleep(10)






if __name__ == "__main__":
    main()
    