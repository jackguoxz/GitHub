import urllib2
from PIL import Image  
from urllib import urlretrieve
from datetime import datetime
import time
import os
import random

# load font modules  (char, image)  
fontMods = []  
for i in range(10):  
    fontMods.append((str(i), Image.open("F:\\lib\\%1d.bmp" % i)))  
proxy_support = urllib2.ProxyHandler({'http':'http://proxy.tencent.com:8080'})  
def recognize(f):  
    im = Image.open(f)  
    im2 = im.convert('1')  
    # check 5 fonts  
    result = ""
    j=0
    x=0
    y=0
    for i in range(4):  
         box = ((x,y,x+10,y+10)) 
        target = im.crop(box)
        j=j+1
        x=x+10
        points = []  
        for mod in fontMods:  
            diffs = 0  
            for yi in range(10):  
                for xi in range(10):  
                    if mod[1].getpixel((xi, yi)) != target.getpixel((xi, yi)):  
                        diffs += 1  
            points.append((diffs, mod[0]))  
        points.sort()  
        result += points[0][1]  
    return result
    #result += ".png"  
    #print "save to", result  
    #im.save(result);  

def display():
    #str2=str(random.randint(0,99))
    str2='b'
    try:
        cookies = urllib2.HTTPCookieProcessor()
        opener = urllib2.build_opener(proxy_support,cookies)
        picture=opener.open('http://www.shjtaq.com/zwfg/validatecode.asp').read()
        filename = str2+".bmp"
        #print filename
        #print filename  # test
        fout = open(filename, "wb")
        fout.write(picture)
        fout.close()
    except Exception, err:
        print "exception"


    #f = opener.open('http://www.shjtaq.com/zwfg/dzjc_new.asp')

    #f = opener.open('http://www.shjtaq.com/zwfg/validatecode.asp')

    #data = '<root>Hello</root>'
    #request = urllib2.Request(
    #        url     = 'http://www.ideawu.net/?act=send',
    #        headers = {'Content-Type' : 'text/xml'},
    #        data    = data)

    #opener.open(request)

    #print cookies

    #def get(link):
    #    urlretrieve(link,'2.bmp')  
      
    #get('http://www.shjtaq.com/zwfg/validatecode.asp');  
    def captcha(inputPic):  
              
            img = Image.open(inputPic) # Your image here!   
            img = img.convert("RGBA")  
      
            pixdata = img.load()  
      
            # Make the letters bolder for easier recognition   
      
            for y in xrange(img.size[1]):  
                    for x in xrange(img.size[0]):  
                             if pixdata[x, y][0] < 90:  
                                     pixdata[x, y] = (0, 0, 0, 255)  
      
            for y in xrange(img.size[1]):  
                    for x in xrange(img.size[0]):  
                             if pixdata[x, y][1] < 136:  
                                     pixdata[x, y] = (0, 0, 0, 255)  
      
            for y in xrange(img.size[1]):  
                    for x in xrange(img.size[0]):  
                            if pixdata[x, y][2] > 0:  
                                    pixdata[x, y] = (255, 255, 255, 255)  
      
            img.save(str2+".gif", "GIF")  
      
            #   Perform OCR using tesseract-ocr library   
      
    if __name__ == '__main__':  
            captcha(str2+'.bmp')  
    im_orig = Image.open(str2+'.gif')
    #big = im_orig.resize((48, 12), Image.NEAREST)
    #ext = ".tif"
    #big.save(str2 + ext)
    #image = Image.open(str2+'.tif')
    #s=image_to_string(image)
    s=recognize(str2+'.gif')
    print s

    #print s[0:4]+'dddd'
    data='cardqz=%BB%A6&carnumber=G10352&type1=02%2F%D0%A1%D0%CD%C6%FB%B3%B5%BA%C5%C5%C6&fdjh=9013637&verify='+s[0:4]+'&act=search&submit=+%CC%E1+%BD%BB+'

    request = urllib2.Request(
                    url     = 'http://www.shjtaq.com/zwfg/dzjc_new.asp',
                    headers = {'Content-Type' : 'application/x-www-form-urlencoded'},
                    data    = data)

    f=opener.open(request)

    length=len(f.read())
    print length
         #os.remove(str2+'.bmp');
    #os.remove(str2+'.gif');
    #os.remove(str2+'.tif');
    #print length
    return length
    
if __name__ == '__main__':  
         #print display()
         j=0
         total=0
         count=0
         while(count<10000):
            t1=time.time()
            count=count+1
             while(display()<33547):
                total=total+1
                print 'again'
            j=j+1
            total=total+1
            res=j*1.0/total
             print j,count,total, '%.3f'% res
             t2=time.time()
             print t2-t1
         print 'total is', total
                   
         
         
