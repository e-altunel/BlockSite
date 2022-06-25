with open("./site_list.txt","r") as f:
    ban = f.readlines()

for a in ban:
    b = a.split("/")
    c = b[2].split(".")
    print(c[-2]+"."+c[-1])
    print(b[2])
    print(a[:-1])
