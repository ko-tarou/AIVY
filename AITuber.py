import requests
import os
import pprint
from openai import OpenAI
import sounddevice as sd
import soundfile as sf
import time


def get_chat_id(youtube_api_key, live_id):
    params = {
        "key":youtube_api_key,
        "part":"liveStreamingDetails",
        "id":live_id
    }
    res = requests.get("https://www.googleapis.com/youtube/v3/videos",
                params=params)
    #print(res.json())
    chat_id = (res.json()
               ["items"][0]
               ["liveStreamingDetails"]
               ["activeLiveChatId"]
               )
    return chat_id

def get_latest_comment(youtube_api_key, chat_id):
    params = {
        "key":youtube_api_key,
        "part":"snippet",
        "liveChatId":chat_id,
        "maxResults":100,
    }
    res = requests.get(
        "https://www.googleapis.com/youtube/v3/liveChat/messages",
        params=params)
    resource = res.json()
    #pprint.pprint(resource) #持ってきたjsonファイルの中身確認
    comments = [x["snippet"]["textMessageDetails"]["messageText"]
                for x in resource["items"]]
    comment = comments[-1]
    return comment
    #print(comments) #取得したコメントのリスト

def get_reply(client, comment):
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
    reply = res.choices[0].message.content
    print(reply) #返信内容の確認
    return reply

def play_reply(comment, reply):
    params = {"text": f"{comment} {reply}","speaker": 3}
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
    youtube_api_key = os.getenv("YOUTUBE_API_KEY") #youtubeAPIを入力(google Cloudから取得) #AIzaSyD84Mzp3Yauc83OCyUOg5XSPAZ2ATHnVOc
    live_id = "InnBtgjAqyU"
    chat_id = get_chat_id(youtube_api_key,live_id)
    client = OpenAI()
    pre_comment = ''
    while True:
        #コメント取得
        comment = get_latest_comment(youtube_api_key,chat_id)
        if pre_comment != comment:
            reply = get_reply(client, comment)
            play_reply(comment, reply)
            pre_comment = comment
        time.sleep(10)

    
    




if __name__ == "__main__":
    main()
    