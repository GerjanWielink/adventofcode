puts File.readlines('input_1').map(&:to_i).each_cons(2).select { |x| x[1] > x[0]}.size
puts File.readlines('input_1').map(&:to_i).each_cons(3).map(&:sum).each_cons(2).select{ |x| x[1] > x[0]}.size
