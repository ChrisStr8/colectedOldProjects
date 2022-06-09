from tkinter import *
from random import randint

'''
this class creates the board then populates the board with the number of bombs
inputed by the player.the class then creates squares in the number of rows and columns
set by the player skps over bombs
'''
class Board:
    def __init__ (self, parent, board_size, bomb_num):
        self.board_frame = Frame(parent)
        self.board_frame.grid(row = 2, column = 0)
        self.parent = parent

        self.board_size = int(size_entry.get())
        self.bomb_num = int(size_entry2.get())

        # list for square objects
        self.list_of_square = []
        self.bomb_list = []
        self.list_of_flags = []
        
        self.bombs_flaged = 0
        self.game_o = False
        
        self.create_bombs()
        self.create_squares()
        self.flag_lable()
        #print (self.list_of_square)

        
    def flag_lable(self):
        #print('flag test')
        Flags_used(self, self.parent)
        
        
    def create_squares(self):
        #creates the board
        row = 0
        while row < self.board_size:
            col = 0
            
            while col < self.board_size:
                coords = (row, col)
                if coords not in self.existing_bomb_coords: #checks there isnt a bomb at the coordinates
                    self.list_of_square.append(Square(row, col, self))
                col += 1
            row += 1
                
        

    def create_bombs(self):
        self.flags = self.bomb_num
        
        self.existing_bomb_coords = set()
        #creates the bombs 
        while len(self.existing_bomb_coords) < self.bomb_num: #creates the number of bombs chosen by the player
            bomb_row = randint(0,self.board_size - 1)
            bomb_col = randint(0,self.board_size - 1)
            bomb_coords = (bomb_row, bomb_col)
            if bomb_coords not in self.existing_bomb_coords: #checks if coordinates allready have a bomb
                self.bomb_list.append(Bomb(bomb_row, bomb_col, self))
                self.existing_bomb_coords.add(bomb_coords)
                #print(bomb_coords)

    def g_over(self):
        game_over(self.parent)

    def win(self):
        you_win(self.parent)
        


        
'''
when left clicked the square checks how many bombs its touching then prints the number touching it if no bombs
touch it then the square will flood fill other squares till one is reached that touches a bomb
when it is right clicked the square wil be flaged and can't be left clicked left clicking a flaged square resets
it
'''
class Square:
    def __init__(self, row_pos, col_pos, board_obj):
        self.col_pos = col_pos
        self.row_pos = row_pos
        self.clicked = 0 

        self.board_obj = board_obj
        self.square = Label(self.board_obj.board_frame, text = ('     '), relief = RAISED)
        self.square.bind('<Button-1>', self.square_clicked)
        self.square.bind('<Button-3>', self.square_flaged)
        self.square.grid(row = row_pos, column = col_pos)
        
        self.flag_img = PhotoImage(file = 'flag.gif')
        #print ('row_pos', row_pos)
        #print ('col_pos', col_pos,'''
#-------''')
         
        
        
        

    def bombs_touched(self, row_pos, col_pos): #checks if the square is touching a bomb
        touched = 0
        touched_squares = []
        touched_squares.append((row_pos, col_pos - 1))
        touched_squares.append((row_pos + 1, col_pos - 1))
        touched_squares.append((row_pos + 1, col_pos))
        touched_squares.append((row_pos + 1, col_pos + 1))
        touched_squares.append((row_pos, col_pos + 1))
        touched_squares.append((row_pos - 1, col_pos + 1))
        touched_squares.append((row_pos - 1, col_pos))
        touched_squares.append((row_pos - 1, col_pos - 1))
        for i in touched_squares:
            if i in self.board_obj.existing_bomb_coords:
                touched += 1
        return touched
                
            
        


    def square_clicked(self, event):
        self.clicked_coords = []
        if self.board_obj.game_o == False:
            #print('begin')
            self.flood_fill(self.row_pos, self.col_pos)
        
        
    def flood_fill(self, row_pos, col_pos):
        #print("floodfill")
        coords = (row_pos, col_pos)
        #print(self.board_obj.list_of_flags)
        if coords in self.board_obj.list_of_flags:
            self.board_obj.flags += 1
            self.board_obj.flag_lable()
        if coords  not in self.clicked_coords:
            if coords  not in self.board_obj.existing_bomb_coords:
                touched = self.bombs_touched(row_pos, col_pos)
            
                if touched > 0:
                    self.square = Label(self.board_obj.board_frame, text = (' ' +str(touched)+ '  '), relief = SUNKEN)
                    self.square.grid(row = row_pos, column = col_pos)
                    self.clicked_coords.append((row_pos, col_pos))
                    #print('''number
#''')
                else:
                    self.square = Label(self.board_obj.board_frame, text = ('     '), relief = SUNKEN)
                    self.square.grid(row = row_pos, column = col_pos)
                    self.clicked_coords.append((row_pos, col_pos))
                
                    if row_pos > 0:
                        self.flood_fill(row_pos - 1, col_pos)
                    if row_pos < self.board_obj.board_size - 1:
                        self.flood_fill(row_pos + 1, col_pos)
                    if col_pos > 0:
                        self.flood_fill(row_pos, col_pos - 1)
                    if col_pos < self.board_obj.board_size - 1:
                        self.flood_fill(row_pos, col_pos + 1)

                    if row_pos > 0 and col_pos > 0:
                        self.flood_fill(row_pos - 1, col_pos - 1)
                    if row_pos > 0 and col_pos < self.board_obj.board_size - 1:
                        self.flood_fill(row_pos - 1, col_pos + 1)
                    if row_pos < self.board_obj.board_size - 1 and col_pos > 0:
                        self.flood_fill(row_pos + 1, col_pos - 1)
                    if row_pos < self.board_obj.board_size - 1 and col_pos < self.board_obj.board_size - 1:
                        self.flood_fill(row_pos + 1, col_pos + 1)
                    #print('''blank
#''')
                      
        

    def square_flaged(self, event):
        if self.board_obj.game_o == False:
            if self.board_obj.flags > 0:
                self.board_obj.flags -= 1
            
                self.board_obj.list_of_flags.append((self.row_pos, self.col_pos))
                self.square = Label(self.board_obj.board_frame, image = self.flag_img, text = ('     '), relief = FLAT)
                self.square.bind('<Button-3>', self.reset)
                self.square.grid(row = self.row_pos, column = self.col_pos)
                self.clicked += 1
                self.board_obj.flag_lable()
            else:
                pass
                #edit to say 'you have used all your flags'
        

    def reset(self, event):
        if self.board_obj.game_o == False:
            self.board_obj.flags += 1
            self.board_obj.list_of_flags.remove((self.row_pos, self.col_pos))
            self.square = Label(self.board_obj.board_frame, text = ('     '), relief = RAISED)
            self.square.bind('<Button-1>', self.square_clicked)
            self.square.bind('<Button-3>', self.square_flaged)
            self.square.grid(row = self.row_pos, column = self.col_pos)
            self.clicked -= 1
            self.board_obj.flag_lable()
            #print(self.board_obj.list_of_flags)

'''
when a bomb is left clicked the game ends. all bombs are reveled, the squares become un-clickable and the function
game_over is run
'''
class Bomb:
    def __init__(self, bomb_row, bomb_col, bomb_obj):
        self.bomb_row = bomb_row
        self.bomb_col = bomb_col
        
        
        self.bomb_num = int(size_entry2.get())

        self.bomb_obj = bomb_obj
        self.square = Label(self.bomb_obj.board_frame, text = ('     '), relief = RAISED)
        self.square.bind('<Button-1>', self.square_clicked)
        self.square.bind('<Button-3>', self.square_flaged)
        self.square.grid(row = bomb_row, column = bomb_col)
        self.bomb_img = PhotoImage(file = 'mine.gif')
        self.flag_img = PhotoImage(file = 'flag.gif')
        while self.bomb_obj.game_o == True:
            self.square = Label(self.bomb_obj.board_frame, image = self.bomb_img, text = ('     '), relief = GROOVE)
            self.square.grid(row = self.bomb_row, column = self.bomb_col) 
            
        
    def square_clicked(self, event):
        self.square = Label(self.bomb_obj.board_frame, image = self.bomb_img, text = ('     '), relief = GROOVE)
        self.square.grid(row = self.bomb_row, column = self.bomb_col)
        #print ('bomb_row', self.bomb_row)
        #print ('bomb_col', self.bomb_col,'''
#       ''')
        self.reveal_coords = []
        self.reveal(self.bomb_row, self.bomb_col)
        self.bomb_obj.game_o = True
        self.bomb_obj.g_over()

    def square_flaged(self, event):
        if self.bomb_obj.flags > 0:
            self.bomb_obj.flags -= 1
            self.bomb_obj.bombs_flaged += 1
            self.square = Label(self.bomb_obj.board_frame, image = self.flag_img, text = ('     '), relief = FLAT)
            self.square.bind('<Button-3>', self.reset)
            self.square.grid(row = self.bomb_row, column = self.bomb_col)
            self.bomb_obj.flag_lable()
            #print (self.bomb_obj.bombs_flaged)
        else:
            pass
            #edit to say 'you have used all your flags'
        if self.bomb_obj.bombs_flaged == self.bomb_num:
            self.bomb_obj.win()
        

    def reset(self, event):
        self.bomb_obj.flags += 1
        self.bomb_obj.bombs_flaged -= 1
        self.square = Label(self.bomb_obj.board_frame, text = ('     '), relief = RAISED)
        self.square.bind('<Button-1>', self.square_clicked)
        self.square.bind('<Button-3>', self.square_flaged)
        self.square.grid(row = self.bomb_row, column = self.bomb_col)
        self.bomb_obj.flag_lable()
        #print (self.bomb_obj.bombs_flaged)

    def reveal(self, row_pos, col_pos):
        coords = (row_pos, col_pos)
        
        if coords  not in self.reveal_coords:
            self.reveal_coords.append((row_pos, col_pos))
            if coords in self.bomb_obj.existing_bomb_coords: 
                self.square = Label(self.bomb_obj.board_frame, image = self.bomb_img, text = ('     '), relief = GROOVE)
                self.square.grid(row = row_pos, column = col_pos) 
               
            if row_pos > 0:
                self.reveal(row_pos - 1, col_pos)
            if row_pos < self.bomb_obj.board_size - 1:
                self.reveal(row_pos + 1, col_pos)
            if col_pos > 0:
                self.reveal(row_pos, col_pos - 1)
            if col_pos < self.bomb_obj.board_size - 1:
                self.reveal(row_pos, col_pos + 1)

'''
displays the number of remaining flags
'''
class Flags_used:
    def __init__(self, board, root):
         
        self.root = root
        self.board = board
        if self.board.game_o == False:
            self.flags_bg = Label(self.root, height = 2, width = 4, relief = SUNKEN)
            self.flags_bg.grid(row = 1, column = 0)

            self.flags_title = Label(self.root, text = ('flags'))
            self.flags_title.grid(row = 0, column = 0)
        
            
            self.flags = Label(self.root, text = (str(self.board.flags)))
            self.flags.grid(row = 1, column = 0)
        


def get_size(root):
    global size_entry
    global size_entry2
    global instruction_label
    global instruction_label2
    global go_btn 
    instruction_label = Label(text = "What board size would you like?")
    instruction_label.grid(row = 1, column = 0)
        
    size_entry = Entry(width = 14)
    size_entry.grid(row = 1, column = 1)

    instruction_label2 = Label(text = "how many bombs would you like?")
    instruction_label2.grid(row = 2, column = 0)

    size_entry2 = Entry(width = 14)
    size_entry2.grid(row = 2, column = 1)
    
    go_btn = Button(text = "Go", command = lambda: push_button(root))
    root.bind('<Return>', lambda e: push_button(root))
    go_btn.grid(row = 3, column = 1)

def push_button(root):
    try:
        if int(size_entry.get()) <= 30: #checks that the board size is less than 30 so that the recursion in flood fill dose not excede the max recursion depth
            board1 = Board(root, int(size_entry.get()), int(size_entry2.get()))
            size_entry.destroy()
            size_entry2.destroy()
            instruction_label2.destroy()
            instruction_label.destroy()
            go_btn.destroy()
         
            new_btn = Button(root, text = "New Game", command = sequence(root.destroy, new_game))
            root.bind('<Return>', lambda e: sequence(root.destroy, new_game))
            new_btn.grid(row = 3, column = 0)
        else:
            er = Tk()
            er.wm_title('Error')
            label1 = Label(er, fg = "black", text = "plese use a number between 1-30", padx = 30, pady = 10,)
            label1.pack()
            
    except ValueError:
        e = Tk()
        e.wm_title('Error')
        label1 = Label(e, fg = "black", text = "plese use a number e.g. 8", padx = 30, pady = 10,)
        label1.pack()
        
       

def game_over(root):
    go = Tk()
    label1 = Label(go, bg = "black", fg = "white", text = "GAME OVER",
                   padx = 30, pady = 10,font=("Times", "24", "bold italic"))
    label1.pack() 
    new_btn = Button(go, text = "New Game", command = sequence(go.destroy, root.destroy, new_game))
    go.bind('<Return>', lambda e: sequence(go.destroy, root.destroy, new_game))
    new_btn.pack()

def you_win(root):
    yw = Tk()
    #print(you_win)
    label1 = Label(yw, bg = "black", fg = "white", padx = 30, pady = 10,
                   text = "YOU WIN", font=("Times", "24", "bold italic"))
    label1.pack()
    new_btn = Button(yw, text = "New Game", command = sequence(yw.destroy, root.destroy, new_game))
    yw.bind('<Return>', lambda e: sequence(yw.destroy, root.destroy, new_game))
    new_btn.pack()


def new_game():
    #print('test')
    main()

def sequence(*functions):
    def func(*args, **kwargs):
        return_value = None
        for function in functions:
            return_value = function(*args, **kwargs)
        return return_value
    return func
    
def main():
    root = Tk()
    root.wm_title('mine_sweper')
    get_size(root)
    root.mainloop() 

        
# main routine
if __name__ == "__main__":
    root = Tk()
    root.wm_title('mine_sweper')
    get_size(root)
    root.mainloop()

