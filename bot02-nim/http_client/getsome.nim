# to execute run following command: nim c -d:ssl -r getsome.nim
import httpclient

const URL = "https://api.github.com/search/repositories" &
            "?q=cat" &
            "&sort=stars" &
            "&order=desc"
let client = newHttpClient()
echo client.getContent(URL)
