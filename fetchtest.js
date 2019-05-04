var FetchTweets = require('fetch-tweets');

var apiKeys = {
    consumer_key :'8SnGnIiZFm8kEHrmeLndpHiZD',
    consumer_secret :'2GtcWlDslfdemvOKL3i09T0p6HqbZ3JEO9ix8032HepyLs2hKj'
};

var fetchTweets = new FetchTweets(apiKeys);

var options = {
  q: 'avengers',
  lang: 'en',
  result_type: 'popular',
  count: 5,
}

fetchTweets.byTopic(options, function(results){
   console.log(results); // Do whatever with the results
});
