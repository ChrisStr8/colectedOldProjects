import sys

def swap(a, b):
        a, b = b, a
        print("str1: "+a)
        print("str2: "+b)

if __name__ == "__main__":
        a = str(sys.argv[1])
        b = str(sys.argv[2])
        swap(a, b)

