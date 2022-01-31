import bs4
import re

def scrapeExpirationDates():
  content = bs4.BeautifulSoup(open('./mainPage.html', 'r'), 'html5lib')
  print(content)
  return

def main():
  scrapeExpirationDates()

main()