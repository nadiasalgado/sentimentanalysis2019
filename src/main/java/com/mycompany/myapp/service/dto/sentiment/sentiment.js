const sentiment = require('sentiment');

number analyse = function(line) {
    var Sentiment = new sentiment();
    var result = Sentiment.analyze(line);
    return result.score;
};
