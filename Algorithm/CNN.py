import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import datetime
import cv2
import glob

### TO RUN: ###
### Make sure training images are in directory of CNN.py,
### and testing images are in the 'testing' directory, which should
### also be located
### in same directory as CNN.py

def toOneHot(arr, num_classes=22):
    newArr = [0]*num_classes
    newArr[arr-1] = 1
    return newArr


labelDict = {"almond": 1, "bivy": 2, "blue": 3, "carrot": 4, "elder": 5, "garlic": 6, "Pgrape": 7,
             "parsnip": 8, "tomatoes": 9, "truemorel": 10, "wildgrape": 11, "berries": 12, "bitter": 13,
             "camas": 14, "creeper": 15, "hemlock": 16, "horsenettle": 17, "Pivy": 18, "monseed": 19,
             "morels": 20, "oak": 21, "sumac": 22}




test_directory = 'testing'

train_images = glob.glob("*.jpg")
test_images = glob.glob(test_directory+"\\*.jpg")

avg_row = avg_col = 0
trainData = [None] * len(train_images)
trainLabels = [None] * len(train_images)
testData = [None] * len(test_images)
testLabels = [None] * len(test_images)

for x,i in enumerate(train_images):
    flag = 0
    for k,v in labelDict.items():
        if k in i:
            trainLabels[x] = toOneHot(v)
            flag = 1
            break

    if flag != 1:
        print(x,i)
        input("flag != 1")


    img = cv2.imread(i)
    shape = np.shape(img)
    avg_row += shape[0]
    avg_col += shape[1]
    trainData[x] = img

avg_row /= len(trainData)
avg_col /= len(trainData)
avg_row = int(avg_row)
avg_col = int(avg_col)

print("Labels: ",np.shape(trainLabels))
print("Data: ",np.shape(trainData))

for x,i in enumerate(test_images):
    flag = 0
    for k,v in labelDict.items():
        if k in i:
            testLabels[x] = toOneHot(v)
            flag = 1
            break

    if flag != 1:
        print(x,i)
        input("flag != 1")


    img = cv2.imread(i)
    shape = np.shape(img)
    testData[x] = cv2.resize(img, (avg_col, avg_row))




for i in range(len(trainData)):
    trainData[i] = cv2.resize(trainData[i], (avg_col, avg_row))
    #print(np.shape(images[i]))

print(np.shape(trainData))
print(np.shape(trainLabels))
print(np.shape(testData))


### Hyperparameters

num_channels = 3
num_outputs = 22

conv_1_neurons = 32
conv_2_neurons = 64
fc_1_neurons = 512

learning_rate = 0.001


lshapes = np.ceil(avg_row/4)*np.ceil(avg_col/4)*conv_2_neurons
lshapes = int(lshapes)
# Graph building functions

def conv2d(x, W):
  return tf.nn.conv2d(input=x, filter=W, strides=[1, 1, 1, 1], padding='SAME')

def max_pool_2x2(x):
  return tf.nn.max_pool(x, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')



tf.reset_default_graph()
sess = tf.InteractiveSession()
x = tf.placeholder("float", shape = [None, avg_row,avg_col,num_channels]) #shape in CNNs is always None x height x width x color channels
y_ = tf.placeholder("float", shape = [None, num_outputs]) #shape is always None x number of classes

W_conv1 = tf.Variable(tf.truncated_normal([5, 5, num_channels, conv_1_neurons], stddev=0.1))#shape is filter x filter x input channels x output channels
b_conv1 = tf.Variable(tf.constant(.1, shape = [conv_1_neurons])) #shape of the bias just has to match output channels of the filter

h_conv1 = tf.nn.conv2d(input=x, filter=W_conv1, strides=[1, 1, 1, 1], padding='SAME') + b_conv1
hconv1shape = tf.shape(h_conv1)

h_conv1 = tf.nn.relu(h_conv1)
h_pool1 = tf.nn.max_pool(h_conv1, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')
hpool1shape = tf.shape(h_pool1)



W_conv2 = tf.Variable(tf.truncated_normal([5, 5, conv_1_neurons, conv_2_neurons], stddev=0.1))
b_conv2 = tf.Variable(tf.constant(.1, shape = [conv_2_neurons]))
h_conv2 = tf.nn.relu(conv2d(h_pool1, W_conv2) + b_conv2)
hconv2shape = tf.shape(h_conv2)
h_pool2 = max_pool_2x2(h_conv2)

#First Fully Connected Layer
hpool2shape = tf.shape(h_pool2)
W_fc1 = tf.Variable(tf.truncated_normal([lshapes, fc_1_neurons], stddev=0.1)) # 7*7*conv_2_neurons
b_fc1 = tf.Variable(tf.constant(.1, shape = [fc_1_neurons]))

h_pool2_flat = tf.reshape(h_pool2, [-1, lshapes]) # 7*7*conv_2_neurons
h_fc1 = tf.nn.relu(tf.matmul(h_pool2_flat, W_fc1) + b_fc1)

#Dropout Layer
keep_prob = tf.placeholder("float")
h_fc1_drop = tf.nn.dropout(h_fc1, keep_prob)

#Second Fully Connected Layer
W_fc2 = tf.Variable(tf.truncated_normal([fc_1_neurons, num_outputs], stddev=0.1))
b_fc2 = tf.Variable(tf.constant(.1, shape = [num_outputs]))

shapet = tf.shape(h_fc1_drop)
#Final Layer
y = tf.matmul(h_fc1_drop, W_fc2) + b_fc2
y_soft = tf.nn.softmax(y)

crossEntropyLoss = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(labels = y_, logits = y))
trainStep = tf.train.AdamOptimizer(learning_rate=learning_rate).minimize(crossEntropyLoss)

correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
sess.run(tf.global_variables_initializer())


tf.summary.scalar('Loss (Cross Entropy)', crossEntropyLoss)
tf.summary.scalar('Accuracy', accuracy)
merged = tf.summary.merge_all()
logdir = "tensorboard/" + datetime.datetime.now().strftime("%Y%m%d-%H%M%S") + "/"
writer = tf.summary.FileWriter(logdir, sess.graph)






epochs = 1000
bestResult = 0
for i in range(epochs):
    #batch = mnist.train.next_batch(batchSize)
    batchImg = trainData
    trainingLabels = trainLabels

    #trainingInputs = batch[0].reshape([batchSize,avg_row,avg_col,num_channels])
    trainingInputs = batchImg
    print("batch shape:",np.shape(trainingInputs))

    if i%10 == 0:
        #summary = sess.run(merged, {x: trainingInputs, y_: trainingLabels, keep_prob: 1.0})
        #writer.add_summary(summary, i)
        pass
    if i%1 == 0:
        trainAccuracy = accuracy.eval(session=sess, feed_dict={x:trainingInputs, y_: trainingLabels, keep_prob: 1.0})
        print("step %d, training accuracy %g"%(i, trainAccuracy))
        print("Loss:",crossEntropyLoss.eval(session=sess, feed_dict={x:trainingInputs, y_: trainingLabels, keep_prob: 1.0}))
        #print("Feeding back into net:", labels[0])
        correct = 0
        wrong = 0
        output = sess.run(y_soft, feed_dict={x: testData, keep_prob: 1.0})

        for f in range(len(output)):
            if np.argmax(testLabels[f]) == np.argmax(output[f]):
                correct += 1
            else:
                wrong += 1
            print("Label:",np.argmax(testLabels[f]), "Output:",np.argmax(output[f]))

        print("Accuracy:",correct,"out of",correct+wrong)
        if correct > bestResult:
            bestResult = correct
            file = open("best.txt", 'a')
            file.write("\nBest: "+str(bestResult))
            file.close()
    sess.run(trainStep, feed_dict={x: trainData, y_: trainLabels, keep_prob: 0.5})
