def sort_dic(dictionary):
    return dict(sorted(dictionary.items(), key=lambda x: x[1], reverse=True))

def build_sorted_freq_dic(text):
    text_letter_list = [x for x in text]
    frequency_dic = {letter: 0 for letter in set(text_letter_list)}
    for i in text_letter_list:
        frequency_dic[i] += 1
    sorted_freq_dic = sort_dic(frequency_dic)
    return sorted_freq_dic

def sort_node_list(node_list):
    return sorted(node_list, key=lambda x: x.freq, reverse=True)

def pair_list_to_dic(input_list):
    dic = {}
    for x in input_list:
        dic[x[0]] = x[1]
    return dic

class Node:
    def __init__(self, char, freq):
        self.right = None
        self.left = None
        self.char = char
        self.freq = freq
    
    def __lt__(self, other):
        return self.freq < other.freq
    
    def __ge__(self, other):
        return self.freq >= other.freq
    
    def __eq__(self, other):
        return self.freq == other.freq
    
    def __str__(self):
        return f'[{self.char}, {self.freq}]'
    
    def __repr__(self):
        return self.__str__()
    
    def merge(self, other):
        new_node = Node(None, self.freq + other.freq)
        new_node.right = self
        new_node.left = other
        return new_node
        
    
def get_node_list(frequency_dic):
    return [Node(char, freq) for char, freq in frequency_dic.items()]

def get_root_node(node_list):
    while(len(node_list) > 1):
        sorted_node_list = sort_node_list(node_list)
        last_node = sorted_node_list.pop(-1)
        second_last_node = sorted_node_list.pop(-1)
        merged_node = last_node.merge(second_last_node)
        sorted_node_list.append(merged_node)
        node_list = sorted_node_list
    return node_list[0]

def generate_codes(root):
    code_list = []
    if root.char:
        code_list.append([root.char, "0"])
        return code_list
    else:
        return generate_codes_help(root, "", code_list)


def generate_codes_help(node, current_code, code_list):

    if node.right is None and node.left is None:
        code_list.append([node.char, current_code])
        return
    
    generate_codes_help(node.right, current_code + "1", code_list)
    generate_codes_help(node.left, current_code + "0", code_list)

    return code_list

def huffman_encode_help(code_dict, text):
    if text == "":
        return ""
    else:
        return code_dict[text[0]] + huffman_encode_help(code_dict, text[1:])


def huffman_encode(text):
    sorted_freq_dic = build_sorted_freq_dic(text)
    node_list = get_node_list(sorted_freq_dic)
    root_node = get_root_node(node_list)
    list_of_codes = generate_codes(root_node)
    dict_of_codes = pair_list_to_dic(list_of_codes)
    encoded_text = huffman_encode_help(dict_of_codes, text)
    return [encoded_text, root_node]

def huffman_decode(encoded_string, root):
    if encoded_string == "":
        return ""
    
    length = 0
    curr_node = root

    for i in encoded_string:
        length += 1
        
        if i == "0":
            curr_node = curr_node.left
        elif i == "1":
            curr_node = curr_node.right

        if curr_node.right is None and curr_node.left is None:
            return curr_node.char + huffman_decode(encoded_string[length::], root)
        