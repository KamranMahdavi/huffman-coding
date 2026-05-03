from huffman import huffman_encode, huffman_decode

text = "Huffman test"

encoded_text, encoded_tree = huffman_encode(text)
decoded_text = huffman_decode(encoded_text, encoded_tree)

print("Text: " + text)
print("Encoded Text: " + encoded_text)
print("Decoded Text: "+ decoded_text)