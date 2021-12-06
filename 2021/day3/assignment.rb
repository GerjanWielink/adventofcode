input = File.readlines('input')

input
  .map{|l| l.scan /\w/}
  .map{|l| l.map(&:to_i)}
  .transpose.map(&:sum)
  .map{|x| x.to_f / input.length > 0.5 ? 1 : 0}
  .join.to_i(2)
  .tap{|x| puts x * (x ^ (input[0].chars.drop(1).map{"1"}.join.to_i(2)))}

def eliminate(input, index, flip = 0)
  bit = ((input.map{|l| l[index].to_i}.sum.to_f / input.length) >= 0.5 ? 1 : 0) ^ flip
  remainder = input.filter{|l| l[index] === bit.to_s}

  remainder.length === 1 ? remainder.first.to_i(2) : eliminate(remainder, index + 1, flip)
end

puts eliminate(input, 0, 0) * eliminate(input, 0, 1)
