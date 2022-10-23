# 課題①: Linear Search
def linear_search(obj, list_numbers):
    for i in range(len(list_numbers)):
        if list_numbers[i] == obj:
            return i
    return None

# 課題②: Global Linear Search
def global_linear_search(obj, arg):
    list = []

    for i, element in enumerate(arg):
        if element == obj:
            list.append(i)
    return list

# 確認用コード：課題①: Linear Search
a = [1,2,3,4,5,6,7,8,9,10]
print(linear_search(4, a))

# 確認用コード:課題②: Global Linear Search
letters = "datasciencehub"
b = [1,2,3,4,5,6,7,8,2,10,2]

print(global_linear_search('a', letters))
print(global_linear_search(2, b))