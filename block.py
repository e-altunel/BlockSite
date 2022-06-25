with open(r"C:\Windows\System32\drivers\etc\hosts","r") as f:
    girdi = f.readlines()

with open("./site_list.txt","r") as f:
    ban = f.readlines()

ban_list = []

for a in ban:
    b = a.split("/")
    c = b[2].split(".")
    a = "127.0.0.1\t" + a
    ban_list = ban_list + [ "127.0.0.1\t" + c[-2]+"."+c[-1]+"\n", "127.0.0.1\t" +b[2]+"\n",a[:-1]+"\n"]

with open(r"C:\Windows\System32\drivers\etc\hosts","w") as f:
    f.writelines(girdi[:21])
    f.writelines(ban_list)

with open("./hosts","w") as f:
    f.writelines(girdi[:21])
    f.writelines(ban_list)