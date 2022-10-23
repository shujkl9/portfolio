import re

def word_count():
# テキストを読み込んで、dataオブジェクトへ格納
    text = open('turtle.txt', 'r')
    data = text.read()

# 必要ない記号等を削除
    data = data.replace(',', '')
    data = data.replace('(', '')
    data = data.replace(')', '')
    data = data.replace('.', '')
    data = data.replace('"', '')
    data = data.replace('—', ' ')
    data = data.replace('\n', ' ')

# 単語単位に分ける
    data = data.split()

# 辞書型の変数を用意
    words = {}

#　辞書型変数wordsにキーを割り当て、キーの出現回数をバリューとする
    for word in data:
        words[word] = words.get(word, 0) + 1
    return words


# word_count関数の呼び出し
words = word_count()

# 要素ごとに改行して表示
for key, value in words.items():
    print(key, value)