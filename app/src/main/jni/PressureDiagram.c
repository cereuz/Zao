#include <jni.h>
#include <stdlib.h>
#include <unistd.h> // sleep 的头文件

//获取压力的方法
int getPressure(){
    return  rand()%801;
}

int  flag = 1;

JNIEXPORT void JNICALL
Java_com_onezao_zao_practices_pressurediagram0306_PressureDiagramActivity_startMonitor(JNIEnv *env,
                                                                                       jobject obj) {

    // TODO
    //1.获取字节码
    jclass  clazz  = (*env) -> FindClass(env,"com/onezao/zao/practices/pressurediagram0306/PressureDiagramActivity");
    //2.找到方法
    jmethodID  methodID = (*env) -> GetMethodID(env,clazz,"setPressure","(I)V");
    //3. 调用方法
    flag =1 ;
    while(flag){
        //在Linux中，sleep   1  就是代表睡一秒钟。
        sleep(1);
        (*env) -> CallVoidMethod(env,obj,methodID,getPressure());
    }

}

/*
 * Class:     com.onezao.zao.practices.pressurediagram0306.MyPressureView
 * Method:    stopMonitor
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_onezao_zao_practices_pressurediagram0306_PressureDiagramActivity_stopMonitor(JNIEnv *env,
                                                                                      jobject obj) {
    // TODO
    flag =0;

}