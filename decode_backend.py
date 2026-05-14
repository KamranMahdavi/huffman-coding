from huffman import huffman_decode_v2
import json, sys

text = sys.argv[1]
file_path = sys.argv[2]

try:
    file = open(file_path)
    file_string = file.read()
    code_dict = json.loads(file_string)

    print(huffman_decode_v2(text, code_dict))
    sys.exit(0)

except json.JSONDecodeError:
    print("Wrong file format.", file=sys.stderr)
    sys.exit(1)

except Exception as e:
    print(f"Invalid input: {str(e)}", file=sys.stderr)
    sys.exit(1)




