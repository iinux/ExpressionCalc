#include<jni.h>
#include<stdio.h>

// https://blog.csdn.net/Thousa_Ho/article/details/78653835

/*
 * Class:     ATest
 * Method:    hello
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_mycompany_helloworld_JNI_hello(JNIEnv * a, jobject b){
   printf("hello,world");
}
