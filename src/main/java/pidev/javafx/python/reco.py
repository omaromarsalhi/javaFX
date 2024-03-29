import speech_recognition as sr

# Initialize recognizer class (for recognizing the speech)
r = sr.Recognizer()

# Reading Microphone as source
# listening the speech and store in audio_text variable

with sr.Microphone() as source:
    audio_text = r.listen(source)
    # recoginize_() method will throw a request error if the API is unreachable, hence using exception handling

    try:
        # using google speech recognition
        print("Welcome our support wanna apreacite for your reclamtion and said : "+r.recognize_google(audio_text))
    except:
        print("Sorry, I did not get that")