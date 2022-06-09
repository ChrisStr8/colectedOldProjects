from tkinter import *
from random import randint

class Board:
    def __init__ (self, parent, board_size, bomb_num):
        self.board_frame = Frame(parent, bg = 'linen')
        self.board_frame.grid(row = 0, column = 0)

        self.board_size = int(size_entry.get())
        self.bomb_num = int(size_entry2.get())

        # list for square objects
        self.list_of_square = []
        self.bomb_list = []
        
        self.create_board()

    def create_board(self):
        
        #creates the board                  
        row = 0
        while row < self.board_size:
            col = 0
            row += 1
            while col < self.board_size:
                self.list_of_square.append(Square(row, col, self))
                col += 1

class Square:
    def __init__(self, col_pos, row_pos, board_obj):
        self.col_pos = col_pos
        self.row_pos = row_pos
        #self.bomb_row = bomb_row
        #self.bomb_col = bomb_col
        self.bomb_num = int(size_entry2.get())
        self.board_size = int(size_entry.get())

        self.board_obj = board_obj
        self.square = Label(self.board_obj.board_frame, text = ('     '), relief = RAISED)
        self.square.bind('<Button-1>', self.square_clicked)
        self.square.grid(row = row_pos, column = col_pos)
        print ('row_pos', row_pos)
        print ('col_pos', col_pos)
        for i in range(self.bomb_num):
            self.bomb_row = randint(0,self.board_size - 1)
            self.bomb_col = randint(1,self.board_size) 

    def square_clicked(self, event):
        if self.row_pos == self.bomb_row and self.col_pos == self.bomb_col:
            self.square = Label(self.board_obj.board_frame, text = ('     '), relief = GROOVE)
            self.square.grid(row = self.row_pos,column = self.col_pos)
        else:
            self.square = Label(self.board_obj.board_frame, text = ('     '), relief = SUNKEN)
            self.square.grid(row = self.row_pos,column = self.col_pos)
        print ('row_pos!', self.row_pos)
        print ('col_pos!', self.col_pos)



def get_size():
    global size_entry
    global size_entry2
    instruction_label = Label(text = "What board size would you like?")
    instruction_label.grid(row = 1, column = 0)
        
    size_entry = Entry(width = 14)
    size_entry.grid(row = 1, column = 1)

    instruction_label2 = Label(text = "how many bombs would you like?")
    instruction_label2.grid(row = 2, column = 0)

    size_entry2 = Entry(width = 14)
    size_entry2.grid(row = 2, column = 1)
      
    go_btn = Button(text = "Go", command = push_button)
    go_btn.grid(row = 3, column = 1)

def push_button():
    try:
        
        board1 = Board(root, int(size_entry.get()), int(size_entry2.get()))
    except ValueError:
        pass
        #edit this to give a mesage saying to give a number
        
        
    

# main routine
if __name__ == "__main__":
    root = Tk()
    root.wm_title('mine_sweper')
    get_size()
    root.mainloop()
