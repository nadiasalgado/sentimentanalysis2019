const sentiment = require('sentiment');
const io = require('console-read-write');

async function main() {
    io.write('Enter String to be sentiment analysed...');
    var line = await io.read();
    var Sentiment = new sentiment();
    var result = Sentiment.analyze(line);
    console.log(result);
}

main();
