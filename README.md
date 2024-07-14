# 💬🐧 chatpiplup
Runelite plugin integration with Ollama through Python Backend to respond to chat messages in Oldschool Runescape
![image](https://github.com/user-attachments/assets/f987e392-5d00-4bb4-a0dc-62c68a1d4a52)

# 📹 VIDEO PREVIEW
https://github.com/user-attachments/assets/7cb135fe-d7b1-4575-bd55-81e46271008f


Requirements:
1. Your custom Runelite plugin
2. Your deployed Ollama model
3. Run Python Backend

## ![image](https://github.com/user-attachments/assets/0835f6a3-9fea-4935-838f-bab33a0bf7a2) Runelite plugin 

Open source Old School RuneScape client <br>
https://github.com/runelite/runelite <br>
Runelite Developer wiki: https://github.com/runelite/runelite/wiki

Set up your own plugin, then incorporate the codes from ```ExamplePlugin.java```, into your plugin. <br>
Essentially, on chat message, sends POST request to Python backend.

## ![image](https://github.com/user-attachments/assets/287a3cbc-7dbb-4a96-90df-14b205067311) Ollama local model deployment
Free, no api needed. unlimited calls. Runs locally which consumes computing resources though, and cannot compete with extremely large language models like GPT-4. But Llama3 works pretty well. <br>
https://ollama.com/ <br>
https://github.com/ollama/ollama

Set up your own model. Python Backend calls llama3 model, but you may use other models available.


## ![image](https://github.com/user-attachments/assets/3e83068c-ce92-44cd-a71f-01beeeeacf95) Python Backend
This python backend ```app.py``` opens a Flask server and accepts the POST request from the Runelite plugin and sends it over to Ollama for a response.
After receiving a response, pyautogui library types the response out.
There is a queue implemented, and the message will only be processed when it is in the front of the queue.

Prompt used: ```Reply in a cute uwu way. Give short response.```
