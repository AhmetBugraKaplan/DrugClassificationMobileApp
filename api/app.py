from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import numpy as np
import pandas as pd


print("Loading model...")
app = Flask(__name__)
CORS(app)

model = joblib.load("../MLmodel/drug_model.pkl")
print("Model loaded.")

@app.route('/', methods=['GET'])
def home():
    return "Drug Prediction API is running."


@app.route('/predict', methods=['POST'])
def predict():
    try:
       data = request.get_json()
       print("Gelen veri:", data)

       age = data['Age']
       Na_to_K = data['Na_to_K']
       Sex_M = data['Sex_M']
       
       BP_LOW = data['BP_LOW']
       BP_NORMAL = data['BP_NORMAL']
       Cholesterol_NORMAL = data['Cholesterol_NORMAL']

       features = np.array([[age, Na_to_K, Sex_M, BP_LOW, BP_NORMAL, Cholesterol_NORMAL]])

       prediction = model.predict(features)
       prediction_result = prediction[0]


       print(f" Tahmin: {prediction_result}")

       response = {
              'prediction': prediction_result
       }
       return jsonify(response)

    except Exception as e:
       print("Hata oluştu:", str(e))
       return jsonify({'error': str(e)}), 400
    
if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0', port=5000)
