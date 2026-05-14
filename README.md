# Huffman Coding

A text compression program that can encode text into binary bits and decode it back using the corresponding key, based on Huffman coding algorithm.

The project contains two implementations:

1. A command-line script
2. A JavaFX GUI application

Both implementations rely on the main Huffman functions module to operate, with the GUI version also offering support for exporting and importing key files in JSON format.


## Project Features

- Encodes text into binary bits using Huffman algorithm
- Decodes binary text back into the original text using the generated key, which is either a Huffman tree or a dictionary of codes and letters
- Provides both command-line and JavaFX GUI implementations
- Separates the core functions module from UIs 


## Project Components

### `huffman.py` (Core Functions Module)

- Builds frequency tables from input text
- Constructs Huffman trees for the text
- Generates prefix-free binary codes using the tree
- Encodes text into binary bits
- Decodes binary bits back into the original text, using either the tree or the dictionary generated for that text
- Handles edge cases like empty input and single-character input

### `huffman_cli.py` (Command-Line Encoder Version)

- Provides an interactive terminal interface
- Prompts the user to input text
- Displays the original text, the encoded text, and the decoded text
- Relies on the core functions from `huffman.py`

### `HuffmanApplication.java` (JavaFX GUI)

- Provides two modes: Encode Mode and Decode Mode
- Invokes Python backend scripts using `ProcessBuilder`
- Displays descriptive error messages in popup windows
- Allows the user to download generated keys in JSON format
- Provides the ability to import the downloaded JSON key files for decoding

### `encode_backend.py`

- Invokes `huffman_encode_v2()` and passes it the input text
- Outputs the encoded text and JSON representation of the key and signals success
- In case of an error, outputs the error message and signals failure

### `decode_backend.py`

- Loads a JSON key file, converting it into a dictionary
- Invokes `huffman_decode_v2()` and passes it both the input text and the dictionary
- Validates that the chosen key file is in the correct JSON format


## How It Works

### Encoding
1. Counts the frequency of each character in the text
2. Constructs a frequency table for each character
3. Builds a Huffman tree based on the frequency table
4. Generates binary codes for each character by traversing the tree
5. Replaces each character of the text with its corresponding code
6. Returns the encoded text and the key file, with the key file being:
    - A binary tree in the command-line version
    - A dictionary mapping letters to their binary codes in the GUI version

### Decoding
- Command-Line version:
    1. Given the Huffman tree for the binary code, traverses the tree based on the digits until it reaches a leaf
    2. replaces the bits with the corresponding character, resets to the root of the tree and repeats until no binary code is left
- GUI version:
    1. Reads each bit in the encoded text and adds them to a string, checking the string against the dictionary each time
    2. when it finds a match in the dictionary, adds the letter to the output, resets the string and repeats until it reaches the end of the encoded text 


## Implementations

### Command-Line Version

Allows the user to encode text directly in the terminal. Outputs the encoded text and the decoded version of the encoded text.

### JavaFX GUI Version

Provides the user with a graphical interface containing two modes:
- Encode mode
- Decode mode

The GUI is able to:
- Save generated keys as `.json` files
- Load the `.json` key files for decoding
- Display descriptive error popups


## Example

```python
encoded_text, encoded_tree = huffman_encode("Hello World!")
decoded_text = huffman_decode(encoded_text, encoded_tree)

print(encoded_text) # Output: 0010100101011110000011111010100010000
print(decoded_text) # Output: Hello World!
```

## Notes

All other files rely on the core `huffman.py` module to run, either directly or indirectly. Therefore, make sure the following files are in the same folder as the module file:

- For the command-line version: 
    - `huffman_cli.py`

- For the JavaFX GUI version:
    - `encode_backend.py`
    - `decode_backend.py`
    - `HuffmanApplication.java`