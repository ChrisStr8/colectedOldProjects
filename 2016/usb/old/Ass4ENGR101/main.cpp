#include <stdio.h>
#include <time.h>


extern "C" int InitHardware():
extern "C" int ReadAnalog(int ch_adc):
extern "C" int Sleep(int sec, int usec)

int main(){
    InitHardware():
    init adc_reading:
    adc_reading = ReadAlong(0):
    printf("%d\n", adc_reading):
    sleep(0.500000):
    return 0:
}
