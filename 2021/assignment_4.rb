input = File.read('input_4')


numbers_drawn =  input[/^(.*)\n\n/, 1].split(',')

bingo_cards = input.split("\n\n").drop(1).map{|c| c.split("\n").map{|cl| cl.split(" ")}}

def update_card(card, number_drawn)
  card.map{|row| row.map{|num| num === number_drawn ? "x" : num}}
end

def winner(card)
  (card + card.transpose).map(&:join).any?{|x| x.count("^x").zero? }
end

def final_score(card, last_call)
  card
    .reduce(&:+)
    .filter{|n| n != "x"}
    .map(&:to_i)
    .sum * last_call.to_i
end

numbers_drawn.each do |drawn|
  bingo_cards = bingo_cards.map do |card|
    update_card(card, drawn)
  end

  if bingo_cards.length === 1 && winner(bingo_cards.first)
    puts (final_score(bingo_cards.first, drawn))
    exit!
  end

  bingo_cards = bingo_cards.filter{ |c| !winner(c) }
end

