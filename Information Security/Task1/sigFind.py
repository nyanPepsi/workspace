def getSign(file, offset, byteCount):
    f = open (file, "rb")
    f.seek(offset)
    s = f.read(byteCount)
    f.close()
    return s

def checkCopies(file, searchTarget, offset, byteCount):
    
