from flask import Flask, request
import ollama
import pyautogui
import json
import time

model = "llama3"
system_prompt = "Reply in a cute uwu way. Give short response."
app = Flask(__name__)

queue = []

@app.route('/')
def hello():
    return 'Hello, World!'

@app.route('/message', methods=['POST'])
async def message():
    # get the post params

    params = json.loads(request.data.decode("utf-8"))
    
    from_user = params['from']
    message = params['message']
    fc = ""

    try:
        fc = params['fc']
    except:
        pass

    # print(from_user, ":", message)
    
    if not message.startswith("Hi piplup"):
        return "bye"
    
    message = message.replace("Hi piplup", "").strip()
    print("replying to message")
    queue.append(message)

    while queue[0] != message:
        time.sleep(1)

    user_prompt=f"{message}"

    response = ollama.chat(
        model=f"{model}",
        messages=[
            {
                "role": "system",
                "content": system_prompt,
            },
            {
                "role": "user",
                "content": user_prompt,
            },
        ],
    )

    result = response["message"]["content"]

    reply = result
    print(reply)

    reply = reply.replace("\n", "")

    # Since osrs chat has limit of 80 characters, we need to split the message into multiple messages
    for i in range(0, len(reply), 77):
        if fc == "Friend Chat":
            pyautogui.typewrite("/")
        elif fc == "Clan Chat":
            # pyautogui.typewrite("//")
            pyautogui.typewrite("///")
        pyautogui.typewrite(reply[i:min(i+77, len(reply))], interval=0.03)
        pyautogui.press('enter')
        time.sleep(1.8)
    queue.pop(0)

    return result

if __name__ == '__main__':
    app.run(port=5001)