from textblob import TextBlob #Make sure you correctly install textblob
import tweepy #you must install tweepy
from tweepy import OAuthHandler 

def __init__(self): #Class constructor
	# secret keys and tokens to access API
    consumer_key = "8SnGnIiZFm8kEHrmeLndpHiZD"
    consumer_secret = "2GtcWlDslfdemvOKL3i09T0p6HqbZ3JEO9ix8032HepyLs2hKj"
    access_token = "1105194942815707136-jNcbwDQR6hfyh0MuLY1gVeszR01hy3"
    access_token_secret = "BklBxUF4fsCQwEKNCBmBxFMqcqhFWGhrJEygbrJbnlMzP"
  
    # attempt authentication 
    try: 
        # create OAuthHandler object 
        self.auth = OAuthHandler(consumer_key, consumer_secret) 
        # set access token and secret 
        self.auth.set_access_token(access_token, access_token_secret) 
        # create tweepy API object to fetch tweets 
        self.api = tweepy.API(self.auth) 
    except: 
        print("Error: Authentication Failed") 

#Basic sentiment analysis function
def analysis(str):
    analysis = TextBlob(str)

    if analysis.sentiment.polarity > 0:
        return 'positive'
    if analysis.sentiment.polarity == 0:
        return 'neutral'
    else:
        return 'negative'

# Main function to perform the sentiment analysis of hardcoded fake tweets in the TestTweets.txt file
def sentimentanalysis():
    with open('TestTweets.txt') as fp: #Referring to the txt the file as fp

        strs = []
        output = []
        for line in fp:
            strs.append(line)

    # Finding all the tweets with the specified words
        Witswords = ['Wits', 'University of the Witswatersrand', 'Wits University', 'wits'] #Words of a paremeter
        Wits = [s for s in strs if any(xs in s for xs in Witswords)]

        WitsPwords = ['WitsProtests']
        Witsprotests = [s for s in strs if any(xs in s for xs in WitsPwords)]

        Protestwords = ['protests', 'Protests', 'Protest', 'protest']
        Protests = [s for s in strs if any(xs in s for xs in Protestwords)]

        Upretoria = ['Tuks', 'UP', 'University of Pretoria', 'university of pretoria']
        UP = [s for s in strs if any(xs in s for xs in Upretoria)]

        uct = ['uct', 'UCT', 'University of Cape Town']
        UCT = [s for s in strs if any(xs in s for xs in uct)]

        AHab = ['Adam','Habib','A Habib']
        Adam = [s for s in strs if any(xs in s for xs in AHab)]

        for s in Wits:
            a = analysis(s)
            output.append(a)

        for s in Witsprotests:
            a = analysis(s)
            output.append(a)

        for s in Protests:
            a = analysis(s)
            output.append(a)

        for s in UP:
            a = analysis(s)
            output.append(a)

        for s in UCT:
            a = analysis(s)
            output.append(a)

        for s in Adam:
            a = analysis(s)
            output.append(a)

    return(output)