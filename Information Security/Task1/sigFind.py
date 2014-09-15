def getSign(file, offset, byteCount):
    f = open (file, "rb")
    f.seek(offset)
    s = f.read(byteCount)
    f.close()
    return s

def checkCopies(file, searchTarget, offset, byteCount):
    import os
    contents = os.listdir(searchTarget)
    signature = getSign(file, offset, byteCount)
    res = []

    for dirname, subdirname, files in os.walk(searchTarget):
        for f in files:
            with open(dirname + '\\' + f, 'rb') as checkFile:
                return checkFile.read()
                if signature in checkFile.read():
                    return checkFile.read()
                    #res.append(os.path.normpath(dirname) + f)
    return res

def findSign(signature, file):
    with open(file, 'rb') as f:
        
    
