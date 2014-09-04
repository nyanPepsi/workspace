import zipfile
import os

def arch(archName, files):
    with zipfile.ZipFile(archName, 'w') as myzip:
        i = iter(files)
        while True:
            try:
                filePath = next(i)
            except StopIteration:
                break
            myzip.write(filePath)
        myzip.close()

def main(argv):
    archName = argv[0]
    pathName = argv[1]
    suffix = ''
    prefix = ''
    
    try:
        suffix = argv[2]
        prefix = argv[3]
    except IndexError:
        pass
    
    os.chdir(pathName)
    contents = os.listdir(pathName)
    pathArray = []
    
    for i in contents:
        if os.path.isfile(i) and (prefix != i[0:len(prefix)] or prefix == ''):
            pathArray.append(i)
            continue
        for dirname, subdirname, files in os.walk(i):
            for j in files:
                if prefix != j[0:len(prefix)] or prefix == '':
                    path = os.path.join(dirname, j)
                    pathArray.append(path)
                    
    if suffix != '':
        pathArray = filter(lambda x: x.endswith(suffix), pathArray)
        
    arch(archName, pathArray)
    
if __name__ == "__main__":
    import sys
    main(sys.argv[1:])

