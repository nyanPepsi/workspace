def getSign(file, offset, byteCount):
    with open (file, "rb") as f:
        f.seek(offset)
        s = f.read(byteCount)
    return s

def checkCopies(file, searchTarget, offset, byteCount):
    import os
    signature = getSign(file, offset, byteCount)
    res = []

    for dirname, subdirname, files in os.walk(searchTarget):
        for f in files:
            curfileSign = getSign(dirname + '/' + f, offset, byteCount)
            if signature == curfileSign:
                res.append(os.path.normpath(dirname + '\\' + f))
    return res

