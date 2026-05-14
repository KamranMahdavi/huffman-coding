from huffman import huffman_encode_v2
import sys, json

text = sys.argv[1]

try:
    encoded_string, encoded_dict = huffman_encode_v2(text)

    print(encoded_string)
    print(json.dumps(encoded_dict))

    sys.exit(0)

except Exception as e:
    print(f"Invalid input: {repr(e)}", file=sys.stderr)
    sys.exit(1)
