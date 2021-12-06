puts File.readlines('input_2').map(&:split).map{|i, s| [i[0], s.to_i]}.map{|i, s| i == 'f' ? [s, 0] : i == 'u' ? [0, -s] : [0, s]}.transpose.map(&:sum).reduce(:*)
puts File.readlines('input_2').map(&:split).map{|i, s| [i[0], s.to_i]}.reduce([0, 0, 0]) { |(a, d, x), (i, s)| i == 'f'? [a, d + (a * s), x + s] : [a + ((i =='u' ? -1 : 1) * s), d, x] }.drop(1).reduce(:*)
