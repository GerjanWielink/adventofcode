puts File.readlines('day1/a').map(&:to_i).each_cons(2).select { |x| x[1] > x[0]}.size
puts File.readlines('day1/a').map(&:to_i).each_cons(3).map(&:sum).each_cons(2).select{ |x| x[1] > x[0]}.size
