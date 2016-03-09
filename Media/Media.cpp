#include "Media.h"

#if defined(_USING_HID)

static const uint8_t _hidReportDescriptor[] PROGMEM = {
  
  //-----------------------------------------------------------------------------

    /* Cross-platform support for controls found on IR Medias */

    0x05, 0x0c,                    //	Usage Page (Consumer Devices)
    0x09, 0x01,                    //	Usage (Consumer Control)
    0xa1, 0x01,                    //	Collection (Application)
    0x85, 0x04,                    //	REPORT_ID (4)
    0x15, 0x00,                    //	Logical Minimum (0)
    0x25, 0x01,                    //	Logical Maximum (1)
    0x09, 0xe9,                    //	Usage (Volume Up)
    0x09, 0xea,                    //	Usage (Volume Down)
    0x75, 0x01,                    //	Report Size (1)
    0x95, 0x02,                    //	Report Count (2)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xe2,                    //	Usage (Mute)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xcd,                    //	Usage (Play)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xb1,                    //	Usage (Pause)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xb7,                    //	Usage (Stop)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xb5,                    //	Usage (Next)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xb6,                    //	Usage (Previous)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xb3,                    //	Usage (Fast Forward)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x09, 0xb4,                    //	Usage (Rewind)
    0x95, 0x01,                    //	Report Count (1)
    0x81, 0x06,                    //	Input (Data, Variable, Relative)

    0x95, 0x06,                    //	Report Count (6) Number of bits remaining in byte
    0x81, 0x07,                    //	Input (Constant, Variable, Relative) 
    0xc0                           //	End Collection
};

//================================================================================
//================================================================================
//	Media
 
Media_::Media_(void)
{
	static HIDSubDescriptor node(_hidReportDescriptor, sizeof(_hidReportDescriptor));
	HID().AppendDescriptor(&node);
}
 
void Media_::begin(void) 
{
}
 
void Media_::end(void) 
{
}
 
void Media_::increase(void)
{
	u8 m[2];
	m[0] = VOLUME_UP;
	m[1] = 0;
	HID().SendReport(4,m,2);
}
 
void Media_::decrease(void)
{
	u8 m[2];
	m[0] = VOLUME_DOWN;
	m[1] = 0;
	HID().SendReport(4,m,2);
}
 
void Media_::mute(void)
{
	u8 m[2];
	m[0] = VOLUME_MUTE;
	m[1] = 0;
	HID().SendReport(4,m,2);
}
 
void Media_::play(void)
{
	u8 m[2];
	m[0] = MEDIA_PLAY;
	m[1] = 0;
	HID().SendReport(4,m,2);
}

void Media_::pause(void)
{
	u8 m[2];
	m[0] = MEDIA_PAUSE;
	m[1] = 0;
	HID().SendReport(4,m,2);
}
 
void Media_::stop(void)
{
	u8 m[2];
	m[0] = MEDIA_STOP;
	m[1] = 0;
	HID().SendReport(4,m,2);
}
 
void Media_::next(void)
{
	u8 m[2];
	m[0] = MEDIA_NEXT;
	m[1] = 0;
	HID().SendReport(4,m,2);
}
 
void Media_::previous(void)
{
	u8 m[2];
	m[0] = MEDIA_PREVIOUS;
	m[1] = 0;
	HID().SendReport(4,m,2);
}
 
void Media_::forward(void)
{
	u8 m[2];
	m[0] = 0;
	m[1] = MEDIA_FAST_FORWARD >> 8;
	HID().SendReport(4,m,2);
}
 
void Media_::rewind(void)
{
	u8 m[2];
	m[0] = 0;
	m[1] = MEDIA_REWIND >> 8;
	HID().SendReport(4,m,2);
}
 
void Media_::clear(void)
{
	u8 m[2];
	m[0] = 0;
	m[1] = 0;
	HID().SendReport(4,m,2);
}

Media_ Media;

#endif
