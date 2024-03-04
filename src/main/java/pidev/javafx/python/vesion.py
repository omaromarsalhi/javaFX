import ollama
import sys

def imageDetection(path):
  with open(path, 'rb') as file:
    response = ollama.chat(
      model='llava',
      messages=[
        {
          'role': 'user',
          'content': 'describe this image and do not exeed 30 words',
          'images': [file.read()],
        },
      ],
    )
  return response['message']['content']





if __name__ == "__main__":
    result = imageDetection(sys.argv[2])
    print(result)
