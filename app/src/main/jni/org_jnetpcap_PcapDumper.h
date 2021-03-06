/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_jnetpcap_PcapDumper */

#ifndef _Included_org_jnetpcap_PcapDumper
#define _Included_org_jnetpcap_PcapDumper
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_jnetpcap_PcapDumper
 * Method:    initIDs
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_PcapDumper_initIDs
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_PcapDumper
 * Method:    dump
 * Signature: (Lorg/jnetpcap/PcapHeader;Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_PcapDumper_dump__Lorg_jnetpcap_PcapHeader_2Ljava_nio_ByteBuffer_2
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     org_jnetpcap_PcapDumper
 * Method:    dump
 * Signature: (Lorg/jnetpcap/PcapHeader;Lorg/jnetpcap/nio/JBuffer;)V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_PcapDumper_dump__Lorg_jnetpcap_PcapHeader_2Lorg_jnetpcap_nio_JBuffer_2
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     org_jnetpcap_PcapDumper
 * Method:    dump
 * Signature: (JIIILjava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_PcapDumper_dump__JIIILjava_nio_ByteBuffer_2
  (JNIEnv *, jobject, jlong, jint, jint, jint, jobject);

/*
 * Class:     org_jnetpcap_PcapDumper
 * Method:    ftell
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_PcapDumper_ftell
  (JNIEnv *, jobject);

/*
 * Class:     org_jnetpcap_PcapDumper
 * Method:    flush
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_jnetpcap_PcapDumper_flush
  (JNIEnv *, jobject);

/*
 * Class:     org_jnetpcap_PcapDumper
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_PcapDumper_close
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
