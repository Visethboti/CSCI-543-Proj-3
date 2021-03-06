# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

from time import perf_counter

board = [
    [8, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 3, 6, 0, 0, 0, 0, 0],
    [0, 7, 0, 0, 9, 0, 2, 0, 0],
    [0, 5, 0, 0, 0, 7, 0, 0, 0],
    [0, 0, 0, 0, 4, 5, 7, 0, 0],
    [0, 0, 0, 1, 0, 0, 0, 3, 0],
    [0, 0, 1, 0, 0, 0, 0, 6, 8],
    [0, 0, 8, 5, 0, 0, 0, 1, 0],
    [0, 9, 0, 0, 0, 0, 4, 0, 0]
]

tableEA1 = [[5, 3, 0, 0, 7, 0, 0, 0, 0],
         [6, 0, 0, 1, 9, 5, 0, 0, 0],
         [0, 0, 0, 0, 0, 0, 0, 6, 0],
         [8, 0, 0, 0, 6, 0, 0, 0, 3],
         [4, 0, 0, 8, 0, 3, 0, 0, 1],
         [7, 0, 0, 0, 2, 0, 0, 0, 6],
         [0, 6, 0, 0, 0, 0, 2, 8, 0],
         [0, 0, 0, 4, 1, 0, 0, 0, 5],
         [0, 0, 0, 0, 8, 0, 0, 7, 9]]

tableEA2 = [[0, 0, 0, 2, 6, 0, 7, 0, 1],
         [6, 8, 0, 0, 0, 0, 0, 9, 0],
         [1, 9, 0, 0, 0, 0, 5, 0, 0],
         [8, 2, 0, 1, 0, 0, 0, 0, 0],
         [0, 0, 4, 6, 0, 2, 9, 0, 0],
         [0, 5, 0, 0, 0, 3, 0, 2, 8],
         [0, 0, 9, 3, 0, 0, 0, 7, 4],
         [0, 4, 0, 0, 5, 0, 0, 3, 6],
         [7, 0, 3, 0, 1, 8, 0, 0, 0]]

tableEA3 = [[5, 0, 1, 0, 0, 0, 6, 0, 4],
         [0, 9, 0, 3, 0, 6, 0, 5, 0],
         [0, 0, 0, 0, 9, 0, 0, 0, 0],
         [4, 0, 0, 0, 0, 0, 0, 0, 9],
         [0, 0, 0, 1, 0, 9, 0, 0, 0],
         [7, 0, 0, 0, 0, 0, 0, 0, 6],
         [0, 0, 0, 0, 2, 0, 0, 0, 0],
         [0, 8, 0, 5, 0, 7, 0, 6, 0],
         [1, 0, 3, 0, 0, 0, 7, 0, 2]]


def solve(bo):
    find = find_empty(bo)
    if not find:  # if find is None or False
        return True
    else:
        row, col = find

    for num in range(1, 10):
        if valid(bo, num, (row, col)):
            bo[row][col] = num

            if solve(bo):
                return True

            bo[row][col] = 0

    return False


def valid(bo, num, pos):

    # Check row
    for i in range(len(bo[0])):
        if bo[pos[0]][i] == num and pos[1] != i:
            return False

    # Check column
    for i in range(len(bo)):
        if bo[i][pos[1]] == num and pos[0] != i:
            return False

    # Check box
    box_x = pos[1] // 3
    box_y = pos[0] // 3

    for i in range(box_y*3, box_y*3 + 3):
        for j in range(box_x*3, box_x*3 + 3):
            if bo[i][j] == num and (i, j) != pos:
                return False

    return True


def print_board(bo):
    for i in range(len(bo)):
        if i % 3 == 0:
            if i == 0:
                print(" ?????????????????????????????????????????????????????????????????????????????????????????????")
            else:
                print(" ?????????????????????????????????????????????????????????????????????????????????????????????")

        for j in range(len(bo[0])):
            if j % 3 == 0:
                print(" ??? ", end=" ")

            if j == 8:
                print(bo[i][j], " ???")
            else:
                print(bo[i][j], end=" ")

    print(" ?????????????????????????????????????????????????????????????????????????????????????????????")


def find_empty(bo):
    for i in range(len(bo)):
        for j in range(len(bo[0])):
            if bo[i][j] == 0:
                return i, j  # row, column

    return None

print('\n--------------------------------------\n')

print('?? Unsolved Suduku :-')
print_board(tableEA3)

print('\n--------------------------------------\n')

t1 = perf_counter()
solve(tableEA3)
t2 = perf_counter()
print('?? Solved Suduku :-')
print_board(tableEA3)

print('\n--------------------------------------\n')

print(f' TIME TAKEN = {round(t2-t1,3)} SECONDS')

print('\n--------------------------------------\n')