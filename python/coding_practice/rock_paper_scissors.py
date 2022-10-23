# ランダムモジュールをインポート
import random

# ユーザー側の出手を決定する関数　r, pまたはsをインプット
def user_choice():
    while True:
        user = input("Let's play a rock-paper-scissors with a PC. Please enter r, p or s: ")
        if user == "r" or user == "p" or user == "s":
            return user
        else:
            print("Please in put a right letter, r, p or s.")


# コンピュータ側の出手を決定する関数
def pc_choice():
    rps =  ["r", "p", "s"]
    pc_choice = random.choice(rps)
    return pc_choice

# それぞれの関数を呼ぶ、出し手を確認し、あいこであればやり直す
while True:
    user = user_choice()
    pc = pc_choice()
    if user == pc:
        print("Your choice is　%s." % user)
        print("PC's choice is %s." % pc )
        print("It's a draw")
    else:
        break

# 双方の出し手を確認
print("Your choice is　%s." % user)
print("PC's choice is %s." % pc )

# 条件分岐で勝敗を決定
if (user == "r" and pc == "p") or (user == "p" and pc == "s") or (user == "s" and pc == "r"):
    print("You lose.")
elif (user == "r" and pc == "s") or (user == "p" and pc == "r") or (user == "s" and pc == "p"):
    print("You win.")
