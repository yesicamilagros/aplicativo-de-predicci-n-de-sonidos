import numpy as np
from python_speech_features import mfcc
import scipy.io.wavfile as wav
import tflite_runtime.interpreter as tflite
from os.path import dirname, join



filename = join(dirname(__file__), "modelo2.tflite")


def modelo_de_prediccion(wav_filename):

    return respuesta
















def wav_to_mfcc(wav_filename):
    """ extract MFCC features from a wav file

    :param wav_filename: filename with .wav format
    :param num_cepstrum: number of cepstrum to return
    :return: MFCC features for wav file
    """


    num_cepstrum=26
    (rate, sig) = wav.read(wav_filename)
    mfcc_features = mfcc(sig, rate, numcep = num_cepstrum)
    p=np.mean(mfcc_features,axis=0)
    k= p.reshape(1,-1)
    return k






def predecir(wav_filenames2):
   filename = join(dirname(__file__), "modelo2.tflite")
   #j=formato(wav_filenames2)
   # import required modules


    # assign files

   ##filename1 = join(dirname(__file__), sound.export(output_file, format="wav"))


    # assign files


   wav_filenamess=str(wav_filenames2)
   m=wav_to_mfcc(wav_filenamess)

   # Load TFLite model and allocate tensors.
   interpreter = tflite.Interpreter(model_path = filename )

    # Get input and output tensors.
   input_details = interpreter.get_input_details()
   output_details = interpreter.get_output_details()

   interpreter.allocate_tensors()

   interpreter.set_tensor(input_details[0]['index'],m.astype(np.float32))

   # run the inference
   interpreter.invoke()

   # output_details[0]['index'] = the index which provides the input
   output_data = interpreter.get_tensor(output_details[0]['index'])
   f=np.argmax(output_data,axis=1)

   if f[0]==0:
       return "steatornis caripensismal"
   elif f[0]==1:
       return "Trochilidae"
   else:
       return "Coprophanaeus lancifer"
 ## [1] es trochilidae

