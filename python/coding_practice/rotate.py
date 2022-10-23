# 課題：Rotate List Challenge
def rotate_list(origin_lst):
    rotated_lst = []
    for lst in origin_list[::-1]:
        rotated_lst.append(lst[::-1])
    return rotated_lst


# 例で確かめる
origin_list = [['a', 'b', 'c', 'd', 'e'],
               ['f', 'g', 'h', 'i', 'j'],
               ['k', 'l', 'm', 'n', 'o'],
               ['p', 'q', 'r', 's', 't']]

print(rotate_list(origin_list))