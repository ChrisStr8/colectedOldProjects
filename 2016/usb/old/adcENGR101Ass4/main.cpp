#include <stdio.h>
#include <time.h>


extern "C" int InitHardware();
extern "C" int ReadAnalog(int ch_adc);
extern "C" int Sleep(int sec, int usec);

int main(){
    InitHardware();
    int adc_reading;
    int min = 0;
    int max = 10000000000000000;
    int reading = 0;
    int halfRange = 0;
    int i = 0;
    while(i < 5){
        reading = ReadAnalog(0);
        adc_reading += reading;
        if(reading > max){
            max = reading;
        }
        if(reading < min){
            min = reading;
        }
        printf("%d\n", "reading"+adc_reading);
        i += 1;
        Sleep(0,500000);
    }
    if(i = 6){
        adc_reading /= 5;
        printf("%d\n", "avrage reading"+adc_reading);
        halfRange = max - min;
        halfRange /= 2
        printf("%d\n", "half range"+adc_reading);
    }
    return 0;
}
