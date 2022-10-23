# 課題 ①：lambda関数を学ぼう
deg_conversion = lambda deg: 1.8 * deg + 32

result = deg_conversion(15.7)
print('{:.1f}'.format(result))

# 課題 ②：lambda関数内に条件分岐を実現しよう
greater_deg_conv = lambda x, y: (1.8 * x + 32) if x > y else (1.8 * y + 32)
result = greater_deg_conv(15.7, 21)
print('{:.1f}'.format(result))
# 69.8

# 課題 ③：lambda関数内に繰り返し処理を実現しよう
temps = [15.7, 21, 17.8, 7.8]
converted_temps =  map(lambda deg: (1.8 * deg + 32), temps)
converted_temps_list = list(converted_temps)

for i in range(len(converted_temps_list)):
    converted_temps_list[i] = round(converted_temps_list[i], 1)
print(converted_temps_list)
# [60.3, 69.8, 64.0, 46.0]