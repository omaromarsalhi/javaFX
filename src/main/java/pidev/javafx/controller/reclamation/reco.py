import speech_recognition as sr

# Initialize recognizer class (for recognizing the speech)
r = sr.Recognizer()

# Function to be called whenever a phrase is recognized
def callback(recognizer, audio):
    try:
        # using google speech recognition
        print("Text: " + recognizer.recognize_google(audio))
    except sr.UnknownValueError:
        print("Google Speech Recognition could not understand audio")
    except sr.RequestError as e:
        print("Could not request results from Google Speech Recognition service; {0}".format(e))

# Reading Microphone as source
# listening the speech and store in audio_text variable
print("Talk")
r.listen_in_background(sr.Microphone(), callback)
print("Listening in background...")

# Keep the program running while it's listening
while True: pass
