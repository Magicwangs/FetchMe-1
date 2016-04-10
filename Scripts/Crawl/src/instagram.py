#coding:utf8
'''
@author: chenguanfu
'''
# -*- coding:utf-8 -*-
import urllib,urllib2,re
import os
import socket
import codecs
def getImage(word):
    # savePath='est/' + str(word)
    # ex = os.path.exists(savePath)
    # if not ex:
        # os.makedirs(savePath)
    # tableEx = os.path.exists(savePath + '/list.table')
    # if not tableEx:
        # ExUrls = []
        # name = 1
    # else:
        # f = open(savePath + '/list.table', 'rb')
        # ExUrls = f.readlines()
        # name = len(ExUrls)
        # f.close()
	max_id = '0'
	for page_num in range(2):
		Page_num = page_num
		if max_id == '0':
			url = 'https://www.instagram.com/explore/tags/' + word
		else:
			url = 'https://www.instagram.com/explore/tags/' + word + '?max_id='+str(max_id)
		print url
        request = urllib2.Request(url)
        response = urllib2.urlopen(request)
       	content = response.read()
		#localHtmlFile = open('instagrama_byssinian.html', 'rb')
		#content = localHtmlFile.read()
		#print content
		#urls=re.findall('{"code":"*","date":"*","dimensions":{*},"comments":*,"likes":{*},"owner":{*},"thumbnail_src":*,"is_video":*,"id":"*","display_src":*}',content,re.I)
		urls=re.findall('"id":"[^"]*","display_src":"[^"]*"',content,re.I)
		count = 0
		for i in urls:
			display_id = re.findall('"id":"[^"]*"', i, re.I)[0]
			display_src = re.findall('"display_src":"[^"]*"', i, re.I)[0]
			real_id = re.findall('"[^"]*"$',display_id,re.I)[0].split('"')[1].strip()
			real_src = re.findall('"[^"]*"$',display_src,re.I)[0].split('"')[1].strip()
			#print real_id
			print real_src
			count = count + 1
			#print "count=" + str(count)
			if count == 12:
				max_id = real_id
				print 
				break
            # if "原图" in i:
                # items = re.findall(r'<ahref="(.+?)">',i)
                # for item in items:
                    # if item + '\n' not in ExUrls:
                        # try:
                            # socket.setdefaulttimeout(10)
                            # u = urllib.urlopen(item)
                            # img = u.read()
                            # tmp = str(name)
                            # f = open(savePath+'/'+tmp+'.jpg','wb')
                            # f.write(img)
                            # f.close()
                            
                            # print item, name
                            # f = open(savePath + '/list.table', 'ab')
                            # f.write(item + '\n')
                            # f.close()
                            # name += 1
                        # except IOError, e:
                            # if hasattr(e,"code"):
                                # print e.code
                            # if hasattr(e,"reason"):
                                # print e.reason
                            # else:
                                # print "OK"

# wordFile = open("AllDogs.txt", "rb")
# for BreedPath in wordFile.readlines():
	# #print BreedPath
	# Breed = BreedPath.split(".")[1].strip()
	# print Breed
	# getImage(Breed)
getImage('abyssinian')
