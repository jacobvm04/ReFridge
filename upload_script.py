import gspread

# talk to mark if you need API access
gc = gspread.oauth(
    credentials_filename="./creds.json",
    authorized_user_filename="./auth.json"
)

sh = gc.open_by_url("https://docs.google.com/spreadsheets/d/18_d9JgwSVnfqihRhx3yClrvzjJasl5RxZFHPJwAftxU/")
wk = sh.worksheet("Harris Teeter")
while True:
    val = input("List: ")
    processed = val.strip("][").split(", ")
    data = [ [x] for x in processed]
    wk.append_rows(values=data)
