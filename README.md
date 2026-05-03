# Huffman Coding

A Python program that enables the user to both encode a text into binary code and decode the binary code back into the text, given the generated tree, using Huffman's algorithm.

## Features

- Creates a frequency table for the characters of the text
- Constructs a Huffman tree based on character frequencies
- Creates codes for each letter based on their position within the tree
- Encodes the text using the generated codes for the letters
- Decodes the binary code back into text given the generated Huffman tree

## How It Works

### Encoding
1. Counts the frequency of each character in the text
2. Constructs a frequency table for each character
3. Creates a node for each character and its frequency in the table
4. Repeatedly merges the two nodes with the lowest frequency into one, until there is only one root node left.
5. Traverses the constructed tree to generate codes for each character.
    - Left branch: `0`
    - Right branch: `1`
6. Recursively replaces each character of the text with its corresponding code

### Decoding
1. Given the Huffman tree for the binary code, traverses the tree based on the digits until it reaches a leaf
2. replaces the bits with the corresponding character, resets to the root of the tree and repeats until no binary code is left

## Example

```python
encoded_text, encoded_tree = huffman_encode("Hello World!")
decoded_text = huffman_decode(encoded_text, encoded_tree)

print(encoded_text) # Output: 0010100101011110000011111010100010000
print(decoded_text) # Output: Hello World!
```