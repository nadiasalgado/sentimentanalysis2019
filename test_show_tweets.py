import unittest
import show_tweets

class KnownValues(unittest.TestCase):
    
    #Test areaofcircle()
    
    def test_to_show_tweetsWits(self):
        #Capture results of the function
        result = show_tweets.show('Wits')
        
        #Check for expected output, entry: 10
        expected = ['"I love Wits"','"I really like food at Wits"','"Wits Students really do exercise their right to protest."','"Wits Vodka Party was pretty average if you ask me. Nothing wow, not bad either."','"Wits Vodka Party last year was so shit. Basically all of my friends got mugged."']
        self.assertEqual(expected, result)


def test_to_show_tweetsTuks(self):
        #Capture results of the function
        result = show_tweets.show('Tuks')
        
        #Check for expected output, entry: 10
        expected = ['"I hate Tuks"',
                    '"I really do not like food at Tuks"',
                    '"Everybody at Tuks basically walks. It\'s not about the car you drive, it\'s about the slops you have. LOL"',
                    '"I am at Tuks, my crush is at UCT. What must happen now?"',
                    '"I heard the real Tuks baddies party at Moloko. I heard out father do too."',
                    '"Tuks of niks."']
        self.assertEqual(expected, result)

# Run the tests
if __name__ == '__main__':
    unittest.main()