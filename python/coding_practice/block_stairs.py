# 階段の段数を特定する関数
def block_stairs(n):
    a = 1
    while True:
        # a段に最低必要なブロック数を計算し、それが入力された引数nより小さい間は、段数を増やしていく        
        if a * (a + 1) / 2 <= n:
            a += 1
        # a段に最低必要なブロック数が、入力された引数nを超えた場合、a-1が引数nによって作成できる階段の段数になる
        else:
            a -= 1
            break
    return a

# 数字を入力する関数
def input_number():
    while True:
        try:
            in_number = eval(input("Please input a number between 1 and 10 to the power of 10: "))
            if 1 <= in_number <= 10 ** 10:
                return in_number
            else:
                print("Please input a number between 1 and 10 to the power of 10.")
        except:
            print("Please input a number between 1 and 10 to the power of 10.")

# 　入力された数字をチェック
i_number = input_number()

# 段数を特定し、出力
print(block_stairs(i_number))

